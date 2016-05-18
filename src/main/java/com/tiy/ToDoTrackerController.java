package com.tiy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sulton on 5/17/2016.
 */

@Controller
public class ToDoTrackerController {
    @Autowired
    ToDoRepository todos;



    @RequestMapping(path = "/add-item", method = RequestMethod.POST)
    public String addGame(String gameName, String gameGenre, boolean isDone) {
        ToDo game = new ToDo(gameName, gameGenre, isDone);
       todos.save(game);
        return "redirect:/";

    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, String genre) {
        List<ToDo> gameList = new ArrayList<ToDo>();
        if (genre != null) {
            gameList = todos.findByToDoType(genre);
        } else {
            Iterable<ToDo> allGames = todos.findAll();
            for (ToDo game : allGames) {
                gameList.add(game);
            }
        }
        model.addAttribute("todos", gameList);
        return "home";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public String deleteGame(Model model, Integer toDOID) {
        if (toDOID != null) {
            todos.delete(toDOID);
        }

        return "redirect:/";
    }

    @RequestMapping(path = "/toggle", method = RequestMethod.GET)
    public String toggle(Model model, Integer toDoID) {
        if (toDoID != null) {
            ToDo todo = todos.findOne(toDoID); ////////
            todo.isDone =  !todo.isDone;

           todos.save(todo);
        }

        return "redirect:/";
    }


}
