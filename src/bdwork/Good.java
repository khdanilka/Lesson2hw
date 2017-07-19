package bdwork;

public class Good {

    private int id;
    private int prodid;
    private String title;
    private int price;

    public Good(int id, int prodid, String title, int price) {
        this.id = id;
        this.prodid = prodid;
        this.title = title;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Товар{" +
                "id = " + id +
                ", prodid = " + prodid +
                ", название = '" + title + '\'' +
                ", цена = " + price +
                '}';
    }

}
