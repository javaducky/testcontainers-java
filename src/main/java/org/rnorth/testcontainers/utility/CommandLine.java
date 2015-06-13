package org.rnorth.testcontainers.utility;

import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;
import org.zeroturnaround.exec.StartedProcess;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Process execution utility methods.
 */
public class CommandLine {
    public static String runShellCommand(String... command) throws IOException, InterruptedException, TimeoutException {
        ProcessResult result;
        result = new ProcessExecutor().command(command)
                .readOutput(true).execute();

        if (result.getExitValue() != 0) {
            System.err.println(result.getOutput().getString());
            throw new IllegalStateException();
        }
        return result.outputUTF8().trim();
    }

    public static StartedProcess runShellCommandInBackground(String... command) throws IOException {
        return new ProcessExecutor()
                .command(command)
                .destroyOnExit()
                .redirectOutput(System.out)
                .redirectError(System.err)
                .start();
    }

    public static boolean executableExists(String executable) {

        // First check if we've been given the full path already
        File directFile = new File(executable);
        if (directFile.exists() && directFile.canExecute()) {
            return true;
        }

        // Otherwise scan the path, trying to find the executable
        return Stream.of(System.getenv("PATH").split(Pattern.quote(File.pathSeparator)))
                .map(Paths::get)
                .filter(path -> Files.exists(path.resolve(executable)))
                .anyMatch(Files::isExecutable);
    }
}
