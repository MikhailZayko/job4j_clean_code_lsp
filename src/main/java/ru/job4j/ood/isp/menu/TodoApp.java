package ru.job4j.ood.isp.menu;

import java.util.Optional;
import java.util.Scanner;

public class TodoApp {

    private static final ActionDelegate DEFAULT_ACTION = () -> System.out.println("Some action");

    private static final String MENU = """
            1. Добавить элемент в корень меню
            2. Добавить элемент к родительскому элементу
            3. Вызвать действие, привязанное к пункту меню
            4. Вывести меню в консоль
            5. Выход
            Выберите опцию:
            """;

    private static final String ADDED = "Элемент успешно добавлен в меню";

    public static void main(String[] args) {
        Menu menu = new SimpleMenu();
        MenuPrinter printer = new Printer();
        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        while (run) {
            System.out.print(MENU);
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    addToRoot(menu, scanner);
                    break;
                case 2:
                    addToParent(menu, scanner);
                    break;
                case 3:
                    callAction(menu, scanner);
                    break;
                case 4:
                    printer.print(menu);
                    break;
                case 5:
                    run = false;
                    break;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
                    break;
            }
        }
    }

    private static void addToRoot(Menu menu, Scanner scanner) {
        System.out.print("Введите имя элемента: ");
        String name = scanner.nextLine();
        menu.add(Menu.ROOT, name, DEFAULT_ACTION);
        System.out.println(ADDED);
    }

    private static void addToParent(Menu menu, Scanner scanner) {
        System.out.print("Введите имя родительского элемента: ");
        String parentName = scanner.nextLine();
        System.out.print("Введите имя дочернего элемента: ");
        String childName = scanner.nextLine();
        boolean result = menu.add(parentName, childName, DEFAULT_ACTION);
        if (result) {
            System.out.println(ADDED);
        } else {
            System.out.println("Родительский элемент не найден");
        }
    }

    private static void callAction(Menu menu, Scanner scanner) {
        System.out.print("Выберите имя пункта меню: ");
        String name = scanner.nextLine();
        Optional<Menu.MenuItemInfo> itemInfo = menu.select(name);
        if (itemInfo.isPresent()) {
            itemInfo.get().getActionDelegate().delegate();
        } else {
            System.out.println("Пункт меню не найден");
        }
    }
}
