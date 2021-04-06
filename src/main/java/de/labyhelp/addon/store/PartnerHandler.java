package de.labyhelp.addon.store;

import de.labyhelp.addon.LabyHelp;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class PartnerHandler {

    @Getter
    private final HashMap<String, String> partner = new HashMap<>();

    public boolean isPartnerIp(String ip) {
        for (Map.Entry<String, String> partner : partner.entrySet()) {
            if (ip.equalsIgnoreCase(partner.getKey()) || ip.equalsIgnoreCase(partner.getValue())) {
                return true;
            }
        }
        return false;
    }

    public String getPartnerIp(String ip) {
        for (Map.Entry<String, String> partner : partner.entrySet()) {
            if (ip.equalsIgnoreCase(partner.getKey())) {
                return partner.getValue();
            } else if (ip.equalsIgnoreCase(partner.getValue())) {
                return partner.getValue();
            }
        }
        return null;
    }

    public void readPartner() {
        partner.clear();
        for (final String entry : LabyHelp.getInstance().getRequestManager().getRequest("https://labyhelp.de/partner.php")) {
            final String[] data = entry.split(":");
            if (data.length == 2) {
                partner.put(data[0], data[1]);
            }
        }
    }
}
