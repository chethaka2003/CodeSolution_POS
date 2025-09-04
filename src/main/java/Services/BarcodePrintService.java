package Services;

import javax.print.*;
import java.nio.charset.StandardCharsets;

public class BarcodePrintService {

    public static void printBarcode(String barcodeText,String itemName,String price,String companyName) throws Exception {
        // 1. Build TSPL command string
        String commands =
                "SIZE 40 mm, 46 mm\n" +
                        "GAP 2 mm, 0 mm\n" +
                        "DENSITY 8\n" +
                        "SPEED 4\n" +
                        "DIRECTION 1\n" +
                        "REFERENCE 0,0\n" +
                        "CLS\n" +
                        "TEXT 55,95,\"1\",0,2,2,\"" + companyName+ "\"\n" +
                        "BARCODE 75,130,\"128\",80,1,0,2,2,\"" + barcodeText + "\"\n" +
                        "TEXT 70,245,\"0\",0,2,2,\"" + itemName + "\"\n" +
                        "TEXT 90,285,\"2\",0,1,1,\"" + price + "\"\n" +
                        "PRINT 1,1\n";

        byte[] data = commands.getBytes(StandardCharsets.US_ASCII);

        // 2. Find printer XP-246B
        PrintService printer = null;
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService ps : services) {
            if (ps.getName().contains("XP-246B")) {
                printer = ps;
                break;
            }
        }
        if (printer == null) {
            throw new RuntimeException("Printer XP-246B not found!");
        }

        // 3. Send raw commands
        DocPrintJob job = printer.createPrintJob();
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(data, flavor, null);
        job.print(doc, null);
    }

    public static void main(String[] args) throws Exception {
        printBarcode("123456789012","Tile 24x24","Rs.15000/=","KAS Ceramics");
    }
}
