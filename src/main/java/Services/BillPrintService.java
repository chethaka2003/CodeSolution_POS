package Services;

import com.codesolution.cs_pos_v1.CartRow;
import com.itextpdf.io.source.ByteArrayOutputStream;

import javax.print.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BillPrintService {

    public static void printReceipt(List<CartRow> cartRows , String companyName, String addressLine1, String addressLine2, String phoneNumber1, String phoneNumber2, double total,int discount,double netAmount) throws Exception {

        String stringDiscount = "Rs."+discount+".00/=";

        String formattedTotal = String.format("%.2f", total);
        String stringTotal = "Rs."+formattedTotal+"/=";

        String formattedNetAmount = String.format("%.2f", netAmount);
        String stringNetAmount = "Rs."+formattedNetAmount+"/=";

        // ESC/POS commands
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Initialize printer
        baos.write(new byte[]{0x1B, 0x40}); // ESC @

        baos.write(new byte[]{0x1B, 0x61, 0x01}); // Align center
        baos.write(new byte[]{0x1B, 0x45, 0x01});   // Bold ON
        baos.write(new byte[]{0x1D, 0x21, 0x11});   // Double width + height

        baos.write((companyName + "\n").getBytes(StandardCharsets.US_ASCII));

        baos.write(new byte[]{0x1B, 0x40}); //RE sets back everything to normal size

        baos.write(("\n").getBytes(StandardCharsets.US_ASCII));     //Line Break

        baos.write(new byte[]{0x1B, 0x61, 0x01}); // Align center

        baos.write((addressLine1 + "\n").getBytes(StandardCharsets.US_ASCII));
        baos.write((addressLine2 + "\n").getBytes(StandardCharsets.US_ASCII));

        baos.write((phoneNumber1+"/"+phoneNumber2 + "\n").getBytes(StandardCharsets.US_ASCII));

        baos.write("------------------------------------------------\n".getBytes());   // Divider


        //Create table columns
        baos.write(new byte[]{0x1B, 0x45, 0x01});   // Bold ON
        baos.write(new byte[]{0x1B, 0x61, 0x00});   //Left align

        // Print headers: Item Name (20 chars), Qty (5 chars), Price (10 chars)
        String header = String.format("%-20s %5s %10s", "Item", "Qty", "Price");
        baos.write((header + "\n").getBytes(StandardCharsets.US_ASCII));

        baos.write(("\n").getBytes(StandardCharsets.US_ASCII));     //Line Break

        baos.write(new byte[]{0x1B, 0x40}); //RE sets back everything to normal size

        // Items
        baos.write(new byte[]{0x1B, 0x61, 0x00}); // Align left
        for (CartRow cartRow : cartRows) {
            String itemName = cartRow.getItemName();
            String qty = cartRow.getQuantity();
            String price = cartRow.getPrice();

            int maxItemLength = 20; // max width for item name

            // Wrap long item names
            while (itemName.length() > maxItemLength) {
                String part = itemName.substring(0, maxItemLength);
                itemName = itemName.substring(maxItemLength);

                // Print wrapped part without qty/price
                baos.write((String.format("%-20s", part) + "\n")
                        .getBytes(StandardCharsets.US_ASCII));
            }

            // Ensure item name in last line is padded to 20 chars
            String paddedName = String.format("%-20s", itemName);

            // Right-align price in a fixed width (10 chars)
            String line = String.format("%s %4s %15s", paddedName, qty, price);
            baos.write((line + "\n").getBytes(StandardCharsets.US_ASCII));
        }

        baos.write("------------------------------------------------\n".getBytes());   // Divider

        baos.write(new byte[]{0x1B, 0x61, 0x00});   //Left align
        baos.write(("TOTAL:                          " + stringTotal + "\n").getBytes(StandardCharsets.US_ASCII));
        baos.write(("Discount:                      -" + stringDiscount + "\n").getBytes(StandardCharsets.US_ASCII));

        baos.write(("\n").getBytes(StandardCharsets.US_ASCII));     //Line Break

        baos.write(("Net Amount:                     " + stringNetAmount + "\n").getBytes(StandardCharsets.US_ASCII));

        baos.write("------------------------------------------------\n".getBytes());   // Divider

        baos.write(new byte[]{0x1B, 0x40}); //RE sets back everything to normal size

        baos.write(("\n").getBytes(StandardCharsets.US_ASCII));     //Line Break

        //Come again
        baos.write(new byte[]{0x1B, 0x61, 0x01}); // Align center
        baos.write(new byte[]{0x1B, 0x45, 0x01});   // Bold ON
        baos.write(("THANK YOU FOR SHOPPING WITH US!" + "\n").getBytes(StandardCharsets.US_ASCII));

        //Before Cut
        baos.write("\n\n\n\n\n".getBytes(StandardCharsets.US_ASCII));

        // Cut paper
        // Full cut
        baos.write(new byte[]{0x1D, 0x56, 0x00});

        byte[] data = baos.toByteArray();

        // Find your thermal printer
        PrintService printer = null;
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService ps : services) {
            if (ps.getName().contains("XP-80C")) { // Change to your receipt printer name
                printer = ps;
                break;
            }
        }
        if (printer == null) {
            throw new RuntimeException("Thermal printer not found!");
        }

        // Send raw commands
        DocPrintJob job = printer.createPrintJob();
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(data, flavor, null);
        job.print(doc, null);
    }



}
