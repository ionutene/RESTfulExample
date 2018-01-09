package org.study.rest;

import org.study.rest.jdbc.JDBCExample;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/json/product")
public class JSONService {

    @GET
    @Path("/get")
    @Produces("application/json")
    public List<Product> getProductInJSON() {

        return JDBCExample.select();

    }

    @POST
    @Path("/post")
    @Consumes("application/json")
    public Response createProductInJSON(Product product) {

        //insert product INTO db

        String result = "Product was not inserted: " + product;

        if (JDBCExample.insert(product)) {
            result = "Product created: " + product;
            System.out.println("Product inserted" + product);
            return Response.status(201).entity(result).build();
        }

        System.out.println("Bad Request: Product vas NOT inserted");
        return Response.status(400).entity(result).build();

    }

    @PUT
    @Path("/put")
    @Consumes("application/json")
    public Response updateProductQtyInJSON(Product product) {

        //try catch with status 200 success vs 500 bad request + internal server error pica db
        String result = "Product was not updated: " + product;
        if (JDBCExample.update(product)) {
            result = "Product created: " + product;
            System.out.println("Product inserted" + product);
            return Response.status(201).entity(result).build();
        }


        return Response.status(201).entity(result).build();
    }

    @DELETE
    @Path("/delete")
    @Consumes("application/json")
    public Response deleteProductQtyInJSON(Product product) {

        //try catch with status 200 success vs 500 bad request + internal server error pica db
        String result = "Product was not deleted: " + product;
        if (JDBCExample.delete(product)) {
            result = "Product deleted: " + product;
            return Response.status(202).entity(result).build();
        }


        return Response.status(405).entity(result).build();
    }

}

