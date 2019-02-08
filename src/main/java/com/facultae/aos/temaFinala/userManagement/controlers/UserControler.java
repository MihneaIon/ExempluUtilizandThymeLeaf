package com.facultae.aos.temaFinala.userManagement.controlers;

import com.facultae.aos.temaFinala.userManagement.model.User;
import com.facultae.aos.temaFinala.userManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserControler {

    @Autowired
    UserRepository userRepository;

    private int globalID=-1;

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "add-user";
    }

    @GetMapping("/signin")
    public String singIn(User user) {
        return "see-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model)
    {
        try {
            userRepository.save(user);
            model.addAttribute("users", userRepository.findAll());
            return "index";
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "index";
    }

    @PostMapping("/logging")
    public String logging(@Valid User user, BindingResult result, Model model)
    {
        List<User> userList=new ArrayList<>();
        userList=(List<User>)userRepository.findAll();
        for(User user1:userList)
        {
            if(user1.getName().equals(user.getName()) &&  user.getPassword().equals(user1.getPassword()))
            {
                model.addAttribute("user", userRepository.findById(user1.getId()));
                globalID=user1.getId();
                model.addAttribute("user",userRepository.findById(globalID));
                System.out.println(userRepository.findById(globalID));
                return "welcome";
            }
        }
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String changePassword(@PathVariable("id") Integer id,Model model)
    {
        User user = userRepository.findById(globalID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "update-user";
//        List<User> myList=new ArrayList<>();
//        myList= (List<User>)userRepository.findAll();
//        for(User auxUser:myList)
//        {
//            if(auxUser.getName().equals(user)&& auxUser.getPassword().equals(oldPass))
//            {
//                auxUser.setPassword(newPass);
//            }
//        }
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        User auxUser=userRepository.findById(id).get();
        if(auxUser==null)
        {
            return "vad";
        }
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }



//    @RequestMapping(path = "/getUser",method = RequestMethod.GET)
//    public List<User> accesYourAccount(){
//        return (List<User>) userRepository.findAll();
////        List<User> listOfUsers=new ArrayList<>();
////        System.out.println("laba");
////        listOfUsers=(List<User>) userRepository.findAll();
////        for (User auxUser:listOfUsers) {
////            if(auxUser.getName().equals("Ion") && auxUser.getPassword().equals("Ion"))
////                System.out.println("laba2");
////                return auxUser;
////        }
////        return null;
//    }
//
//
//    @RequestMapping(path = "/deleteAcccount/{id}",method = RequestMethod.DELETE)
//    public ResponseEntity deleteYourAccount(@PathVariable(value = "id")Integer id)
//    {
//        User myUser=userRepository.findById(id).get();
//        if(myUser == null){
//            return ResponseEntity.badRequest().build();
//        }
//        userRepository.delete(myUser);
//        return ResponseEntity.ok().build();
//    }
//
//    @RequestMapping(path = "/updatePassword/{id}", method = RequestMethod.PUT)
//    public ResponseEntity changeYourPassword(@PathVariable(value = "id")Integer id,
//                                            @Valid @RequestBody User myUser){
//        User auxUser= userRepository.findById(id).get();
//        if(auxUser==null)
//        {
//            return ResponseEntity.badRequest().build();
//        }
//        auxUser.setPassword(myUser.getPassword());
//        User updateUser=userRepository.save(auxUser);
//        return ResponseEntity.ok().build();
//    }
//
////    @RequestMapping(path = "/createAccount",method = RequestMethod.POST)
////    public User creareCont(@RequestBody User user)
////    {
////        return userRepository.save(user);
////    }
//
//
//    @RequestMapping(path = "/changePass", method = RequestMethod.PUT)
//    public void changePassword(String oldPass, String newPass, String user ,@RequestBody  User newUser)
//    {
//        List<User> myList=new ArrayList<>();
//        myList= (List<User>)userRepository.findAll();
//        for(User auxUser:myList)
//        {
//            if(auxUser.getName().equals(user)&& auxUser.getPassword().equals(oldPass))
//            {
//                auxUser.setPassword(newPass);
//            }
//        }
//    }
//
//    @RequestMapping(path = "/createAccount",method = RequestMethod.POST)
//    public User createAnUser(/*String userName, String password, */ @RequestBody User reteinUser)
//    {
//        List<User> myList=new ArrayList<>();
//        myList= (List<User>)userRepository.findAll();
//        System.out.println(myList);
//        for(User auxUser:myList)
//        {
//            if(auxUser.getName().equals(reteinUser.getName()))
//            {
//                System.out.println("Usser already exist");
//                return null;
//            }
//        }
//        return userRepository.save(reteinUser);
//    }

}
