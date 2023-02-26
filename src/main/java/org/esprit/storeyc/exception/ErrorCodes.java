package org.esprit.storeyc.exception;

public enum ErrorCodes {
    PRODUCT_NOT_FOUND(1000),
    PRODUCT_NOT_VALID(1001),
    CATEGORY_NOT_FOUND(2000),
    ORDER_NOT_FOUND(3000),
    ORDER_NOT_VALID(3001),
    CUSTOMER_NOT_FOUND(4000),
    CART_NOT_FOUND(5000),
    CART_ITEM_NOT_FOUND(6000),
    PAYMENT_NOT_FOUND(7000),
    COMMANDE_NOT_FOUND(8000),
    COMMAND_NOT_VALID(8001);

    private int errorCode;

    /*La classe ErrorCodes contient une énumération de codes d'erreur possibles pour le projet. Chaque code d'erreur est associé à un entier unique qui représente le code d'erreur réel.*/
    ErrorCodes(int errorCode) {
        this.errorCode = errorCode;
    }
    /*La méthode getErrorCode() est une méthode d'instance qui retourne l'entier associé à un code d'erreur spécifique.*/
    public int getErrorCode() {
        return errorCode;
    }
    /*En utilisant cette énumération, chaque exception peut être créée avec un code d'erreur associé. Les codes d'erreur peuvent être utilisés pour identifier facilement le type d'erreur qui s'est produit lorsqu'une exception est levée, ce qui facilite le débogage et la résolution des problèmes.*/
}

