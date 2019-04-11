package ch.heigvd.smtp.client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

public class ConfigLoader {
    final private String configPath = "./conf/config.json";
    final private String prankPath = "./conf/prank.json";

    private JSONObject config;
    private JSONObject prank;

    public ConfigLoader() {
        File configFile = new File(configPath);
        File prankFile = new File(prankPath);

        FileInputStream configReader;
        try {
            configReader = new FileInputStream(configFile);

            FileInputStream prankReader = new FileInputStream(prankFile);

            byte[] data = new byte[(int) configFile.length()];
            configReader.read(data);
            configReader.close();

            config = new JSONObject(new String(data, "UTF-8"));

            data = new byte[(int) prankFile.length()];
            prankReader.read(data);
            prankReader.close();

            prank = new JSONObject(new String(data, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MailHeader> getMailHeaders() {
        Random random = new Random();
        ArrayList<MailHeader> res = new ArrayList<>();
        // mailing list
        JSONArray mailing = prank.getJSONArray("mailing");
        // number of groups to create
        int nbGroups = prank.getInt("nb_groups");

        // generate groups
        for (int i = 0; i < nbGroups; ++i) {
            // random size of group
            int size = random.nextInt(mailing.length() - 3) + 3;
            // random list of indexes for the mailing array
            final int[] indexes = random.ints(0, mailing.length()).distinct().limit(size).toArray();

            // generate group
            MailHeader mh = new MailHeader();
            mh.setSender(mailing.getString(indexes[0]));
            for (int j = 1; j < size; ++j) {
                mh.addReceiver(mailing.getString(indexes[j]));
            }

            res.add(mh);
        }

        return res;
    }

    public ArrayList<MailContent> getMailContents() {
        ArrayList<MailContent> res = new ArrayList<>();
        JSONArray mails = prank.getJSONArray("mails");

        for (int i = 0; i < mails.length(); ++i) {
            MailContent mc = new MailContent();
            mc.setSubject(mails.getJSONObject(i).getString("subject"));
            mc.setMessage(mails.getJSONObject(i).getString("message"));

            res.add(mc);
        }

        return res;
    }

    public String getHostname() {
        return config.getString("hostname");
    }

    public int getPort() {
        return config.getInt("port");
    }

    public String getDomain() {
        return config.getString("domain");
    }

    public Level getLogLevel() {
        return Level.parse(config.getString("log_level"));
    }

    public Credential getCredentials() {
        JSONObject credentials = config.getJSONObject("credentials");
        return new Credential(credentials.getString("username"), credentials.getString("password"));
    }
}
