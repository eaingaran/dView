package xyz.fullstack.development.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.fullstack.development.dao.SourceRepository;
import xyz.fullstack.development.dao.UserRepository;
import xyz.fullstack.development.domain.Source;
import xyz.fullstack.development.domain.User;

@Controller
@RequestMapping(path = "/dREST")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SourceRepository sourceRepository;

    @GetMapping(path = "/addSource") // Map ONLY GET Requests
    public @ResponseBody
    String addNewSource(@RequestParam String name
            , @RequestParam String path) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Source source = new Source();
        source.setName(name);
        source.setPath(path);
        sourceRepository.save(source);
        return "Path Added";
    }

    @GetMapping(path = "/getSources")
    public @ResponseBody
    Iterable<Source> getSource() {
        // This returns a JSON
        return sourceRepository.findAll();
    }

    @GetMapping(path = "/getUsers")
    public @ResponseBody
    Iterable<User> getUsers() {
        // This returns a JSON
        return userRepository.findAll();
    }
}
