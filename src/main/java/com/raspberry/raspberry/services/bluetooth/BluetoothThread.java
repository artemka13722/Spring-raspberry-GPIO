package com.raspberry.raspberry.services.bluetooth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raspberry.raspberry.dto.BluetoothDto;
import lombok.extern.slf4j.Slf4j;

import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.*;

@Slf4j
public class BluetoothThread extends Thread{

    private final String bluetoothKey;

    private final String key;

    public BluetoothThread(String bluetoothKey, String key) {
        this.bluetoothKey = bluetoothKey;
        this.key = key;
    }

    @Override
    public void run() {

        try {
            UUID uuid = new UUID("1101", true);
            String connectionString = "btspp://localhost:" + uuid + ";name=Sample SPP Server";

            StreamConnectionNotifier streamConnNotifier = (StreamConnectionNotifier) Connector.open(connectionString);

            log.info("Server Started. Waiting for clients to connect...");

            while (true) {
                StreamConnection connection = streamConnNotifier.acceptAndOpen();

                RemoteDevice dev = RemoteDevice.getRemoteDevice(connection);
                log.info("Remote device address: " + dev.getBluetoothAddress());
                log.info("Remote device name: " + dev.getFriendlyName(true));

                InputStream inStream = connection.openInputStream();
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
                String lineRead = bReader.readLine();

                BluetoothDto bluetoothDto = new BluetoothDto();
                if (lineRead.equals(bluetoothKey)) {
                    bluetoothDto.setMessage("Login Success");
                    bluetoothDto.setKey(key);
                } else {
                    bluetoothDto.setMessage("Error");
                }

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(bluetoothDto) + "\n";

                OutputStream outStream = connection.openOutputStream();
                PrintWriter pWriter = new PrintWriter(new OutputStreamWriter(outStream));
                pWriter.write(json);
                pWriter.flush();
                pWriter.close();
            }
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
    }
}
