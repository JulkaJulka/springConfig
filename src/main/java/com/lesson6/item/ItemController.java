package com.lesson6.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ItemController {


    private DAO dao;

    @Autowired
    public ItemController(DAO dao) {
        this.dao = dao;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/Item/save", produces = "application/json")
    public @ResponseBody
    String save(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Item item = convertJSONStringToItem(req);
        dao.save(item);
        return "ok";

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/Item/delete", produces = "text/plain")
    protected @ResponseBody
    String delete(HttpServletRequest req, HttpServletRequest res) throws IOException {

        String id = req.getParameter("idItem");
        Long idItem = Long.parseLong(id);

        try {

            dao.delete(idItem);
            return "OK" + "Item id " + idItem + " was deleted successfully";

        } catch (BadRequestException e) {
            e.printStackTrace();
            return "Deleting unsuccessful " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/Item/update", produces = "application/json")
    public @ResponseBody
    String update(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Item item = convertJSONStringToItem(req);
        try {
            dao.update(item);
            return "ok" + "Item id " + item.getId() + " was updated successfully";
        } catch (BadRequestException e) {
            e.printStackTrace();
            return "Update unsuccessful " + e.getMessage();
        }

    }

    private Item convertJSONStringToItem(HttpServletRequest req) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = req.getInputStream()) {
            Item item = mapper.readValue(is, Item.class);

            return item;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
