package Services;

import com.itextpdf.io.source.ByteArrayOutputStream;

import javax.print.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BillPrintService {

    public static void printReceipt(List<String> items, String companyName, String addressLine1,String addressLine2,String phoneNumber1,String phoneNumber2,double total) throws Exception {
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

        baos.write("-------------------------------------------------\n".getBytes());   // Divider


        //Create table columns
        baos.write(new byte[]{0x1B, 0x45, 0x01});   // Bold ON
        baos.write(new byte[]{0x1B, 0x61, 0x00});   //Left align

        // Print headers: Item Name (20 chars), Qty (5 chars), Price (10 chars)
        String header = String.format("%-20s %5s %10s", "Item", "Qty", "Price");
        baos.write((header + "\n").getBytes(StandardCharsets.US_ASCII));

        baos.write(new byte[]{0x1B, 0x40}); //RE sets back everything to normal size

        // Items
        baos.write(new byte[]{0x1B, 0x61, 0x00}); // Align left
        for (String item : items) {
            baos.write((item + "\n").getBytes(StandardCharsets.US_ASCII));
        }

        baos.write("-------------------------------------------------\n".getBytes());   // Divider

        baos.write(new byte[]{0x1B, 0x61, 0x00});   //Left align
        baos.write(("TOTAL:                          " + total + "\n").getBytes(StandardCharsets.US_ASCII));
        baos.write(("Discount:                      -" + total + "\n").getBytes(StandardCharsets.US_ASCII));

        baos.write("-------------------------------------------------\n".getBytes());   // Divider

        baos.write(("Net Amount:                     " + total + "\n").getBytes(StandardCharsets.US_ASCII));

        baos.write("-------------------------------------------------\n".getBytes());   // Divider

        baos.write(new byte[]{0x1B, 0x40}); //RE sets back everything to normal size

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


    public static void main(String[] args) throws Exception {
        List<String> names = new ArrayList<String>();
        names.add("ppp");
        names.add("ppp");
        printReceipt(names,"Shalika Super","No 555/C ,Egoda Uyana Road","Walana ,Panadura","070 50 67 377","038 22 44 172",500);
    }
}
