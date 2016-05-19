package com.tiy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
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
    public String addItem(HttpSession session, String gameName, String gameGenre, boolean isDone) {
        User user = (User) session.getAttribute("user");
        ToDo game = new ToDo(gameName, gameGenre, isDone, user);
       todos.save(game);
        return "redirect:/";

    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session, String toDoType) {

        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }

        User saveduser = (User) session.getAttribute("user");

        List<ToDo> toDOList = new ArrayList<ToDo>();

        if (saveduser != null) {
            if(toDoType != null){
                toDOList = todos.findByToDoTypeAndUser(toDoType ,saveduser);

            }else {
                toDOList = todos.findByUser(saveduser);
            }



            toDOList = todos.findByToDoTypeAndUser(toDoType ,saveduser);
        } else {
            Iterable<ToDo> allGames = todos.findAll();
            for (ToDo toDo : allGames) {
                toDOList.add(toDo);
            }

            if (toDoType != null) {
                toDOList = todos.findByToDoType(toDoType);
            }


            }
            model.addAttribute("todos", toDOList);
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

    @Autowired
    UserRepository users;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password) throws Exception {
        User user = users.findFirstByName(userName);
        if (user == null) {
            user = new User(userName, password);
            users.save(user);
        }
        else if (!password.equals(user.password)) {
            throw new Exception("Incorrect password");
        }
        session.setAttribute("user", user);
//        session.setAttribute("users", userName);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostConstruct
    public void init() {
        if (users.count() == 0) {
            User user = new User();
            user.name = "Zach";
            user.password = "hunter2";
            users.save(user);
        }
    }


}
