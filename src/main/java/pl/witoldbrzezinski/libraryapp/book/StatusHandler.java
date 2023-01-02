package pl.witoldbrzezinski.libraryapp.book;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class StatusHandler {

    private List<BookEntity> books = new ArrayList<>();

    void setExpiredBooksStatus(){

    }

}
