import bdwork.Good;
import bdwork.SQLDataBase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        SQLDataBase sqlDataBase = new SQLDataBase();

        sqlDataBase.init();
        sqlDataBase.createDB();
        sqlDataBase.clearDB();
        try {
            sqlDataBase.addDataToDB("товар", 10);
        } catch (SQLException e) {
            throw new RuntimeException();
        }

        String inputStr;

        while (true) {
            inputStr = in.nextLine();
            String[] arrInputWords = inputStr.split(" ");
            if (arrInputWords[0].equals("/цена")){
                checkTitle(sqlDataBase,arrInputWords[1]);
            }else if (arrInputWords[0].equals("/сменитьцену")) {
                updatePrice(sqlDataBase,arrInputWords[1],arrInputWords[2]);
            }else if (arrInputWords[0].equals("/товарыпоцене")){
                getGoodWithPrice(sqlDataBase,arrInputWords[1],arrInputWords[2]);
            }else break;
        }

        sqlDataBase.dispose();
    }

    public static void checkTitle(SQLDataBase sqlDataBase, String title){
        try {
            Good good = sqlDataBase.checkGoodWithTitle(title);
            if (good != null) System.out.println(good);
            else System.out.println("товара с таким именем нет");
        } catch (SQLException e) {
            throw new RuntimeException("не удалось проверить элемент");
        }
    }

    public static void updatePrice(SQLDataBase sqlDataBase, String title, String price){
        int priceInt = Integer.parseInt(price);
        try {
            if (sqlDataBase.updateGood(title,priceInt) > 0)
                System.out.println("цена товара с именем " + title + " успешно обновлена");
            else System.out.println("обновление цены не удалось");
        } catch (SQLException e) {
            throw new RuntimeException("не удалось обновить товар");
        }
    }

    public static void getGoodWithPrice(SQLDataBase sqlDataBase, String down, String up){
        int priceDown = Integer.parseInt(down);
        int priceUp = Integer.parseInt(up);
        try {
            ArrayList<Good> good = sqlDataBase.getGoodsWithPriceBetween(priceDown,priceUp);
            if (good.size() == 0) System.out.println("Товары в текущем диапазоне не найдены");
            else System.out.println(good);
        } catch (SQLException e) {
            throw new RuntimeException("не удалось получить диапазон");
        }
    }
}
