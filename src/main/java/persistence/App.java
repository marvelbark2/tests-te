package persistence;

public class App {
    public static void main(String[] args) {
        try {
            new ContactView();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try{
//            DataSource source = new DataSource(50);
//            Contacts contacts = new Contacts(source);
//            for (int i = 0; i < 10; i++) {
//                contacts.read(1);
//                contacts.closeConnection();
//                if(i == 10/2 - 1)
//                    System.out.println("-------------------------");
//            }
//        } catch (Exception e) {e.printStackTrace();}
    }
}
