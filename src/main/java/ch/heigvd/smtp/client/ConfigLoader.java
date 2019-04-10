package ch.heigvd.smtp.client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class ConfigLoader {
    final private String configPath = "./conf/config.json";
    final private String prankPath = "./conf/prank.json";

    private JSONObject config;
    private JSONObject prank;

    public ConfigLoader() {
        File configFile = new File(configPath);
        File prankFile = new File(prankPath);

        FileInputStream configReader = null;
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
        ArrayList<MailHeader> res = new ArrayList<>();
        JSONArray groups = prank.getJSONArray("groups");

        for (int i = 0; i < groups.length(); ++i) {
            MailHeader mh = new MailHeader();
            mh.setSender(groups.getJSONObject(i).getString("sender"));

            JSONArray receivers = groups.getJSONObject(i).getJSONArray("receivers");
            for (int j = 0; j < receivers.length(); ++j) {
                mh.addReceiver(receivers.getString(j));
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

    public Credential getCredentials() {
        JSONObject credentials = config.getJSONObject("credentials");
        return new Credential(credentials.getString("username"), credentials.getString("password"));
    }
}
