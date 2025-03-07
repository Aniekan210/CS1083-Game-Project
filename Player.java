import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Player {
        public final SimpleIntegerProperty serialNumber;
        private final SimpleStringProperty name;
        private final SimpleIntegerProperty roundNum;
        private final SimpleIntegerProperty rowNum;
        private final SimpleIntegerProperty vBucksWon;

        public Player(int serialNumber, String name, int roundNum, int rowNum, int vBucksWon) {
            this.serialNumber = new SimpleIntegerProperty(serialNumber);
            this.name = new SimpleStringProperty(name);
            this.roundNum = new SimpleIntegerProperty(roundNum);
            this.rowNum = new SimpleIntegerProperty(rowNum);
            this.vBucksWon = new SimpleIntegerProperty(vBucksWon);
        }

        public int getSerialNumber() {
            return serialNumber.get();
        }

        public String getName() {
            return name.get();
        }

        public int getRoundNum() {
            return roundNum.get();
        }

        public int getRowNum() {
            return rowNum.get();
        }

        public int getVBucksWon() {
            return vBucksWon.get();
        }
    }