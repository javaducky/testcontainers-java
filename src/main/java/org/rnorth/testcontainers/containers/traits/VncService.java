package org.rnorth.testcontainers.containers.traits;

/**
 * A container which exposes a VNC server.
 */
public interface VncService {

    int DEFAULT_PORT = 5900;

    /**
     * @return a URL which can be used to connect to the VNC server from the machine running the tests. Exposed for convenience, e.g. to aid manual debugging
     */
    String getVncAddress();

    /**
     * @return the VNC password for this server
     */
    String getPassword();

    /**
     * @return the port number on which the VNC server is running
     */
    default int getPort() {
        return DEFAULT_PORT;
    }
}
