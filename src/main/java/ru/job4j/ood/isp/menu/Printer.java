package ru.job4j.ood.isp.menu;

public class Printer implements MenuPrinter {

    @Override
    public void print(Menu menu) {
        menu.forEach(item -> {
            int depth = (int) item.getNumber().chars().filter(ch -> ch == '.').count() - 1;
            System.out.println("----".repeat(depth) + item.getNumber() + " " + item.getName());
        });
    }
}
