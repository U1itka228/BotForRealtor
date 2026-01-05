import model.ArchiveCustomer;
import model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.List;
public class WorkWithCustomerDB {

    private SessionFactory sessionFactory;





    public WorkWithCustomerDB(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // TODO Создание клиента
    public void createCustomer(String name, String phone, String city, String type,
                               LocalDateTime timeToContact, Long realtorID, String location, String formOfPayment, String heatingLevel, String shortDescription) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Customer user = new Customer(name, phone, city, type, timeToContact, realtorID, location, formOfPayment, heatingLevel, shortDescription);
            session.save(user);

            transaction.commit();
            if(session != null){
                session.close();
            }
            System.out.println("Клиент сохранен!");

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Ошибка сохранения: " + ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }

    //TODO путь к файлам (к первому или второму)
    public String createFileListAndReturnPath(int choice) {
        FileWriter fileWriter = null;
        String path = null;
        if (choice == 1){
            List<Customer> customers = null;
            path = "src/main/resources/lists/Список клиентов на рассмотрении.txt";
            try {
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();

                //запись в список customers
                Query<Customer> query = session.createQuery("FROM model.Customer", Customer.class);
                customers = query.list();

                //упаковка в файл в удобном формате
                fileWriter = new FileWriter(path);
                for (int i = 0; i < customers.toArray().length; i++) {
                    fileWriter.write(customers.get(i).toString());
                }
                fileWriter.close();
                transaction.commit();
                session.close();
            } catch (Exception e) {
                e.getMessage();
            }
        } else if (choice==2) {
            List<ArchiveCustomer> archiveCustomers = null;
            path = "src/main/resources/lists/Список клиентов из архива.txt";
            try {
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();

                //запись в список customers
                Query<ArchiveCustomer> query = session.createQuery("FROM model.ArchiveCustomer", ArchiveCustomer.class);
                archiveCustomers = query.list();

                //упаковка в файл в удобном формате
                fileWriter = new FileWriter(path);
                for (int i = 0; i < archiveCustomers.toArray().length; i++) {
                    fileWriter.write(archiveCustomers.get(i).toString());
                }
                fileWriter.close();
                transaction.commit();
                session.close();
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return path;
    }
    //TODO Customer из основной таблицы(возвращает customer)
    public Customer getCustomerByPhoneNumber(String phoneNumber)
    {
        Customer customer = null;
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Query<Customer> query = session.createQuery("FROM model.Customer WHERE phone = "+phoneNumber, Customer.class);
            customer = query.getSingleResult();

            transaction.commit();
            session.close();
        }catch (Exception e){
            e.getMessage();
        }
        return customer;
    }
    //TODO ArchiveCustomer из основной таблицы(возвращает ArchiveCustomer)
    public ArchiveCustomer getArchiveCustomerByPhoneNumber(String phoneNumber)
    {
        ArchiveCustomer archiveCustomer = null;
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Query<ArchiveCustomer> query = session.createQuery("FROM model.ArchiveCustomer WHERE phone = "+phoneNumber, ArchiveCustomer.class);
            archiveCustomer = query.getSingleResult();

            transaction.commit();
            session.close();
        }catch (Exception e){
            e.getMessage();
        }
        return archiveCustomer;
    }


//TODO добавление в архив и удаление из основного
    public void createArchiveCustomer(Customer customerForArchive){
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            //Archive archiveCustomer = null;

            ArchiveCustomer archiveCustomer = new ArchiveCustomer(customerForArchive);
            session.save(archiveCustomer);

           session.delete(customerForArchive);
            transaction.commit();
            session.close();
        }catch (Exception e){
            System.out.println("Ошибка: "+e.getMessage());
        }
    }
    //TODO добавление в рассмотрение и удаление из архива
    public void createQuestionableCustomer(ArchiveCustomer archiveCustomerForTransfer, LocalDateTime timeToContact){
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            //Archive archiveCustomer = null;

            Customer customer = new Customer(archiveCustomerForTransfer);
            customer.setTimeToContact(timeToContact);
            session.save(customer);

           session.delete(archiveCustomerForTransfer);
            transaction.commit();
            session.close();
        }catch (Exception e){
            System.out.println("Ошибка: "+e.getMessage());
        }
    }


    // TODO Удаление клиента
        public void deleteCustomer(Integer id) {
        Session session = null;
        Transaction transaction = null;

        try {
            Customer customer = session.get(Customer.class, id);
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            if(customer != null){
                session.delete(customer);
                transaction.commit();
                if(session != null){
                    session.close();
                }
                System.out.println("Клиент с id: " + id + " удален");
            }else {
                System.out.println("Клиент с id: " + id + " не найден");
            }
        }catch (Exception ex){
            if(transaction != null){
                transaction.rollback();
                System.out.println("Ошибка удаления клиента");
                System.out.println(ex.getMessage());
            }
        }
    }

    public void updateName(Integer id, String newName) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                customer.setName(newName);
                session.update(customer);
                transaction.commit();
                if (session != null) session.close();
                System.out.println("Имя клиента обновлено: " + newName);
            } else {
                System.out.println("Клиент с id " + id + " не найден");
            }
        } catch (Exception ex) {
            if (transaction != null) transaction.rollback();
            System.out.println("Ошибка обновления имени клиента: " + ex.getMessage());
        }
    }

    public void updatePhone(Integer id, String newPhone) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                customer.setPhone(newPhone);
                session.update(customer);
                transaction.commit();
                if (session != null) session.close();
                System.out.println("Номер телефона клиента обновлен: " + newPhone);
            } else {
                System.out.println("Клиент с id " + id + " не найден");
            }
        } catch (Exception ex) {
            if (transaction != null) transaction.rollback();
            System.out.println("Ошибка обновления номера клиента: " + ex.getMessage());
        }
    }

    public void updateType(Integer id, String newType) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                customer.setType(newType);
                session.update(customer);
                transaction.commit();
                if (session != null) session.close();
                System.out.println("Тип недвижимости клиента обновлен: " + newType);
            } else {
                System.out.println("Клиент с id " + id + " не найден");
            }
        } catch (Exception ex) {
            if (transaction != null) transaction.rollback();
            System.out.println("Ошибка обновления типа клиента: " + ex.getMessage());
        }
    }

    public void updateCity(Integer id, String newCity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                customer.setCity(newCity);
                session.update(customer);
                transaction.commit();
                if (session != null) session.close();
                System.out.println("Город клиента обновлен: " + newCity);
            } else {
                System.out.println("Клиент с id " + id + " не найден");
            }
        } catch (Exception ex) {
            if (transaction != null) transaction.rollback();
            System.out.println("Ошибка обновления города клиента: " + ex.getMessage());
        }
    }


    public void updateRealtorID(Integer id, Long newRealtorID) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                customer.setRealtorID(newRealtorID);
                session.update(customer);
                transaction.commit();
                if (session != null) session.close();
                System.out.println("Агент клиента обновлен: " + newRealtorID);
            } else {
                System.out.println("Клиент с id " + id + " не найден");
            }
        } catch (Exception ex) {
            if (transaction != null) transaction.rollback();
            System.out.println("Ошибка обновления агента клиента: " + ex.getMessage());
        }
    }

    public void updateTimeToContact(Integer id, LocalDateTime newTime) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                customer.setTimeToContact(newTime);
                session.update(customer);
                transaction.commit();
                if (session != null) session.close();
                System.out.println("Время контакта клиента обновлено: " + newTime);
            } else {
                System.out.println("Клиент с id " + id + " не найден");
            }
        } catch (Exception ex) {
            if (transaction != null) transaction.rollback();
            System.out.println("Ошибка обновления времени контакта клиента: " + ex.getMessage());
        }
    }


}
