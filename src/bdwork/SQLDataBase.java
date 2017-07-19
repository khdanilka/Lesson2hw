package bdwork;


import java.sql.*;
import java.util.ArrayList;

public class SQLDataBase implements BDConnection {

    private Connection connection;
    private Statement statement;


    @Override
    public void init() {
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:goods.sqlite");
            statement = connection.createStatement();
        }catch (ClassNotFoundException | SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void createDB() {
        try {
            statement.execute("CREATE TABLE if not exists 'goods' " +
                                "('id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                 "'prodid' INTEGER UNIQUE, " +
                                 "'title' STRING, " +
                                 "'price' INT);");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Таблица создана или уже существует.");
    }


    @Override
    public void clearDB() {

        try {
            statement.execute("DELETE FROM 'goods' ");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void dispose() {

        try{
            connection.close();
        } catch (SQLException e){
            throw  new RuntimeException(e);
        }

    }

    public void addDataToDB(String title, int initPrice) throws SQLException {

        connection.setAutoCommit(false);
        PreparedStatement ps = connection.prepareStatement
                ("INSERT INTO goods (prodid, title, price) VALUES(?, ?, ?);");
        for (int i = 1; i < 10001; i ++){
                ps.setInt(1,i);
                ps.setString(2, title + i);
                ps.setInt(3,initPrice * i);
                ps.addBatch();
        }
        ps.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);

    }


    public Good checkGoodWithTitle(String title) throws SQLException{

        Good good;

        PreparedStatement ps = connection.prepareStatement
        ("SELECT * FROM Goods WHERE TITLE = ?");
        ps.setString(1,title);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            good = new Good(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getInt(4)
                   );
            return good;
        }

        return null;
    }

    public int updateGood(String title,int price) throws SQLException{
        PreparedStatement ps = connection.prepareStatement
                ("UPDATE Goods SET price = ? WHERE title = ?");
        ps.setInt(1,price);
        ps.setString(2,title);
        return ps.executeUpdate();
    }


    public ArrayList<Good> getGoodsWithPriceBetween(int down, int up) throws SQLException{
        ArrayList<Good> arrGood = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM Goods WHERE Price BETWEEN ? AND ?");
        ps.setInt(1,down);
        ps.setInt(2,up);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Good good = new Good(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getInt(4)
                        );
            arrGood.add(good);
        }
        return arrGood;
    }

}
