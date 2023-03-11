package org.esprit.storeyc.services.stripe;



import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    public String charge(String cardToken, int amount, String currency) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        ChargeCreateParams params = new ChargeCreateParams.Builder()
                .setAmount((long) amount)  // convertir la valeur amount en Long
                .setCurrency(currency)
                .setSource(cardToken)
                .build();

        Charge charge = Charge.create(params);

        if (charge.getStatus().equals("succeeded")) {
            return "Paiement réussi!";
        } else {
            return "Erreur de paiement.";
        }
    }
}
/*Dans cet exemple, vous devez fournir un jeton de carte (card token) au lieu d'une carte.
 Vous pouvez obtenir un jeton de carte à partir du formulaire de paiement côté client.
 Le montant de la charge est en cents, donc si vous voulez effectuer une charge de 10 euros, vous devez passer 1000 comme paramètre amount.*/