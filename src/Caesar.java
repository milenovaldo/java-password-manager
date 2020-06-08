public class Caesar {

    // Declraing the variables 
    private int key;

    
    // Constructor
    public Caesar(int var1) {
        this.key = var1;
    }

    // This function is to encrypt the password for us
    public String encrypt(String var1) {
        // Initiazing the variable
        int var2 = this.key % 26;
        if (var2 < 0) {
            var2 += 26;
        }
        // Rotating the text
        return this.rotate(var2, var1);
    }

    public String decrypt(String var1) {
        // Initializing the vairbale to decrypt
        int var2 = this.key * -1 % 26;
        if (var2 < 0) {
            var2 += 26;
        }
        // Rotating the text
        return this.rotate(var2, var1);
    }

    // To rotate the text
    protected String rotate(int var1, String var2) {
        String var3 = "";
        // Lopping for var 2 
        for (int var4 = 0; var4 < var2.length(); ++var4) {
            // Checking if small alphabets
            if (var2.charAt(var4) <= 'z' && var2.charAt(var4) >= 'a') {
                var3 = var3 + (char) ((var2.charAt(var4) - 97 + var1) % 26 + 97);
                // Checking if big alphabets
            } else if (var2.charAt(var4) <= 'Z' && var2.charAt(var4) >= 'A') {
                var3 = var3 + (char) ((var2.charAt(var4) - 65 + var1) % 26 + 65);
            } else {
                var3 = var3 + var2.charAt(var4);
            }
        }
        return var3;
    }
}
