package com.myprj.crawler.web.controller.target;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myprj.crawler.domain.target.ProductData;
import com.myprj.crawler.service.target.ProductService;
import com.myprj.crawler.web.controller.AbstractController;
import com.myprj.crawler.web.dto.JsonResponse;
import com.myprj.crawler.web.enumeration.DTOLevel;

/**
 * @author DienNM (DEE)
 */

@Controller
@RequestMapping(value = "/targets/products", produces = "application/json")
public class ProductController extends AbstractController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable(value = "id") long id,
            @RequestParam(value = "level", defaultValue = "DEFAULT") DTOLevel target) {

        ProductData product = productService.getById(id);
        if (product == null) {
            JsonResponse jsonResponse = new JsonResponse(false);
            jsonResponse.putMessage("Product ID " + id + " not found");
            return jsonResponse;
        }

        JsonResponse response = new JsonResponse(true);
        response.putData(product);

        return response;
    }
}
