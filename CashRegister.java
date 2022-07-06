// Homework 1: Sales Register Program
// Course: CIS357
// Due date: July 5, 2022
// Name: Tanner J Koth
// GitHub:
// Instructor: Il-Hyung Cho
// Program description: This program emulates a cash register at a grocery store. A .txt file is read to an array of
// object instances each containing product code, item name, and unit price. We then prompt the user for input (y/n),
// which determines if a sale is to be conducted. The process of the sale involves prompting the user for more input,
// and calling on various methods to process that input. When the user has finished their purchase they will enter (-1)
// as a product code, which will then call on various methods to generate a receipt in ascending order by item name. The
// program will then prompt the user with the decision to begin another sale. If true, repeat instructions above; if
// false, the program prints the total sale for the day, and thanks the user for using the system.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CashRegister {

    /**
     * This is the main method for the program that contains the most important logic
     * @param args
     */
    public static void main(String[] args) {
        int itemCode;
        int count = 0;
        float dailyTotal = 0.0f;
        final float TAX_RATE = 1.06f;

        Item itemArray[] = readFile();
        PurchaseInfo[] pInfo = new PurchaseInfo[50];

        System.out.println("Welcome to Koth cash register system!\n");

        Scanner sc = new Scanner(System.in); // Create scanner object

        System.out.print("Beginning a new sale (Y/N) ");
        char c = sc.next().charAt(0);
        c = Character.toUpperCase(c);

        while(c == 'Y'){
            if(c == 'Y'){
                System.out.println("--------------------");
                do {
                    itemCode = itemCode(sc);
                    while((itemCode < 1 || itemCode > 10) && itemCode!=-1){
                        System.out.println(("!!! Invalid product code"));
                        itemCode = itemCode(sc);
                    }

                    if(itemCode == -1){
                        break;
                    }

                    String name = itemName(itemArray, itemCode);

                    int quantity = itemQuantity(itemCode, sc);
                    while(quantity < 1 || quantity > 9) {
                        System.out.println("!!! Invalid quantity");
                        quantity = itemQuantity(itemCode, sc);
                    }

                    float price = itemTotal(itemArray, itemCode, quantity);
                    pInfo[count] = new PurchaseInfo(price, quantity, name);
                    count++;

                } while (itemCode!=-1);

                dailyTotal += listOverview(pInfo, TAX_RATE, count); // Call list overview function to print list

                System.out.print("Beginning a new sale (Y/N) ");
                c = sc.next().charAt(0);
                c = Character.toUpperCase(c);
            }
        }
        if(c == 'N'){
            System.out.println("\nThe total sale for the day is  $  " + String.format("%.2f",dailyTotal) + "\n");
            System.out.println("Thanks for using POST system. Goodbye.");
        }
    }

    /**
     * This method reads in the contents of the file into an array of object instances
     * @return
     */
    public static Item[] readFile(){

        Item itemArray[];
        itemArray = new Item[10];

        File inFile = new File("itemTable.txt");

        if(inFile.exists()){
            try{
                Scanner sc = new Scanner(inFile);

                int i = 0;
                while(sc.hasNextLine()){
                    String line = sc.nextLine();
                    String[] tokens = line.split(",");
                    int itemCode = Integer.parseInt(tokens[0]);
                    String itemName = tokens[1];
                    float unitPrice = Float.parseFloat(tokens[2]);
                    itemArray[i] = new Item(itemCode, itemName, unitPrice);
                    i+=1;
                }
                return itemArray;
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        else {
            System.out.println("File does not exist");
            System.exit(0);
        }
        return itemArray;
    }

    /**
     * This method accepts the item code for each item for sale.
     * @param sc
     * @return
     */
    public static int itemCode(Scanner sc){
        System.out.print("Enter product code:  ");
        int itemCode = sc.nextInt();
        return itemCode;
    }

    /**
     * This method gets the item name and prints the name.
     * @param itemArr
     * @param itemCode
     * @return
     */
    public static String itemName(Item itemArr[], int itemCode){
        String itemName = itemArr[itemCode-1].getItemName();
        System.out.println("         item name: " + itemName);
        return itemName;
    }

    /**
     * This method accepts the quantity of the item.
     * @param itemCode
     * @param sc
     * @return
     */
    public static int itemQuantity(int itemCode, Scanner sc){
        int quantity;
        System.out.print("Enter quantity: ");
        quantity = sc.nextInt();
        return quantity;
    }

    /**
     * This method computes the item total for each item.
     * @param itemArr
     * @param itemCode
     * @param quantity
     * @return
     */
    public static float itemTotal(Item itemArr[],int itemCode, int quantity) {
        float price = itemArr[itemCode-1].getUnitPrice() * quantity;
        System.out.println("        item total: $   " + String.format("%.2f", price)+"\n");
        return price;
    }

    /**
     *  This method lists the quantity, name, and price for all sale items in sorted order, and gets the subtotal of
     *  all items purchased.
     * @param pInfoArr
     * @param TAX_RATE
     * @param count
     * @return
     */
    public static float listOverview(PurchaseInfo pInfoArr[], float TAX_RATE, int count) {
        float total = 0.0f;

        System.out.println("----------------------------");
        System.out.println("Items list: ");

        PurchaseInfo[] sortedArr = new PurchaseInfo[pInfoArr.length];
        sortedArr = listSort(pInfoArr, count);

        for(PurchaseInfo objInst : sortedArr){
            if(objInst.getItemName() != null && objInst.getPrice()!=0.0f && objInst.getQuantity()!=0){
                total += objInst.getPrice();
                System.out.print("\t" + objInst.getQuantity() +
                        " " + objInst.getItemName() +
                        "\t $   " +String.format("%.2f",objInst.getPrice())+"\n");
            }
        }

        System.out.println("Subtotal             $   "+String.format("%.2f",total));
        float totalWithTax = calculateTotal(total, TAX_RATE);
        System.out.println("Total with Tax (6%)  $   "+String.format("%.2f",totalWithTax));
        System.out.print("Tendered amount      $   ");
        Scanner sc = new Scanner(System.in);
        float amtTendered = Float.parseFloat(sc.next());
        while(amtTendered < totalWithTax) {
            System.out.println("Please enter a valid amount.");
            amtTendered = Float.parseFloat(sc.next());
        }
        float change = amtTendered - totalWithTax;
        System.out.println("Change               $   "+String.format("%.2f",change));
        System.out.println("----------------------------");
        return totalWithTax;
    }

    /**
     * This method computes the total sales amount with tax.
     * @param total
     * @param TAX_RATE
     * @return
     */
    public static float calculateTotal(float total, float TAX_RATE){
        float totalWithTax = 0.0f;
        totalWithTax = total * TAX_RATE;
        return totalWithTax;
    }

    /**
     *
     * @param pInfoArr
     * @param count
     * @return
     */
    public static PurchaseInfo[] listSort(PurchaseInfo[] pInfoArr, int count) {
        PurchaseInfo[] sortedArr = new PurchaseInfo[count];
        for(int i = 0; i <count; i++){
            for(int j = i+1; j<count; j++){
                if (pInfoArr[i].getItemName() != null && pInfoArr[j].getItemName() != null) {
                    if ((pInfoArr[i].getItemName()).compareTo(pInfoArr[j].getItemName()) > 0){
                        PurchaseInfo temp = new PurchaseInfo(pInfoArr[i].getPrice(),pInfoArr[i].getQuantity(),pInfoArr[i].getItemName());
                        pInfoArr[i] = pInfoArr[j];
                        pInfoArr[j] = temp;
                    }
                }
            }
        }
        for(int i =0; i < count; i++){
            sortedArr[i] = pInfoArr[i];
        }
        return sortedArr;
    }
}
