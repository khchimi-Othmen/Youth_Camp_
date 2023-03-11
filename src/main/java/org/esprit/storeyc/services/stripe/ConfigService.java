package org.esprit.storeyc.services.stripe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConfigService {
    @Autowired
    private Environment env;

    private final Map<String, String> configs = new HashMap<>();

    public ConfigService() {
        // initialize configs from a properties file or database
        configs.put("maxItemCount", "100");
        configs.put("allowGuestCheckout", "true");
        configs.put("defaultCurrency", "USD");
    }

    public String getConfig(String key) {
        return configs.get(key);
    }

    public void setConfig(String key, String value) {
        configs.put(key, value);
    }

    public Map<String, String> getAllConfigs() {
        return Collections.unmodifiableMap(configs);
    }

    public String getCardToken() {
        return env.getProperty("stripe.cardToken");
    }

    public String getCurrencyCode() {
        return env.getProperty("stripe.currencyCode");
    }
}
