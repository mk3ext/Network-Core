package me.mk3ext.dev.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerDataInfo {
    private final String motd;
    private final int playersOnline;
    private final int maxPlayers;

    private ServerDataInfo(final String motd, final int playersOnline, final int maxPlayers) {
        this.motd = motd;
        this.playersOnline = playersOnline;
        this.maxPlayers = maxPlayers;
    }

    public static ServerDataInfo getServerInfo(final String host, final int port) {
        try {
            final Socket socket = new Socket(host, port);
            final InputStream in = socket.getInputStream();
            final OutputStream out = socket.getOutputStream();
            out.write(254);
            final byte[] answereBytes = new byte[256];
            in.read(answereBytes);
            final byte[] newBytes = new byte[105];
            int remove = 0;
            for (int i = 0; i < 128; ++i) {
                if (answereBytes[2 * i] <= 31 && answereBytes[2 * i] >= 0) {
                    ++remove;
                } else {
                    newBytes[i - remove] = answereBytes[2 * i];
                }
            }
            String answere = new String(newBytes, "Cp1252");
            answere = answere.substring(1);
            if (answereBytes[0] == 255) {
                return null;
            }
            final String splitByte = String.valueOf('§');
            final String[] info = answere.split(splitByte);
            if (info.length != 3) {
                return null;
            }
            return new ServerDataInfo(info[0], new Integer(info[1].trim()), new Integer(info[2].trim()));
        } catch (IOException ex) {
        }
        return null;
    }

    public String getMOTD() {
        return this.motd;
    }

    public Integer getOnlinePlayers() {
        return this.playersOnline;
    }

    public Integer getMaxPlayers() {
        return this.maxPlayers;
    }

    @Override
    public String toString() {
        return String.format("%s (%d/%d)", this.motd, this.playersOnline, this.maxPlayers);
    }
}
// EXAMPLE USAGE

//final ServerDataInfo servername = ServerDataInfo.getServerInfo("127.0.0.1", 25572);
//
//servername.getMotd();
////
//servername.getOnlinePlayers();
