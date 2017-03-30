/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author juuso_j0pbwen
 */

public class FileUserDao implements UserDao {

    ArrayList<User> users = new ArrayList<User>();
    File file;
    
    
    public FileUserDao(String address) throws FileNotFoundException {
        try {
            this.file = new File(address);
            file.createNewFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        readUsers(file);
    }

    @Override
    public List<User> listAll() {
        return users;
    }

    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void add(User user) {

        FileWriter writer;
        try {
            writer = new FileWriter(file, true);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("User not added");
            return;
        }

        try {
            writer.write(user.getUsername() + "_§_" + user.getPassword());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("User not added");
            return;
        }
        try {
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("User not added");
            return;
        }
        users.add(user);
    }

    private void readUsers(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] items = line.split("_§_");
            users.add(new User(items[0], items[1]));
        }
    }

}
