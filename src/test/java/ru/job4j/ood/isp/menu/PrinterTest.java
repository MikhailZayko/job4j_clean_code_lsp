package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;
import static ru.job4j.ood.isp.menu.SimpleMenuTest.STUB_ACTION;

class PrinterTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        outContent.reset();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(System.out);
    }

    private Menu getCompletedMenu() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        return menu;
    }

    @Test
    public void whenPrinted() {
        Menu menu = getCompletedMenu();
        MenuPrinter printer = new Printer();
        printer.print(menu);
        String expected = String.join(System.lineSeparator(),
                "1. Сходить в магазин",
                "----1.1. Купить продукты",
                "--------1.1.1. Купить хлеб",
                "--------1.1.2. Купить молоко",
                "2. Покормить собаку",
                "");
        assertThat(outContent.toString()).isEqualTo(expected);
    }
}