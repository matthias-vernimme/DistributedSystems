package be.kuleuven.foodrestservice.controllers;

import be.kuleuven.foodrestservice.domain.Meal;
import be.kuleuven.foodrestservice.domain.MealsRepository;
import be.kuleuven.foodrestservice.domain.Order;
import be.kuleuven.foodrestservice.exceptions.MealNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class MealsRestController {

    private final MealsRepository mealsRepository;

    @Autowired
    MealsRestController(MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
    }

    @GetMapping("/rest/meals/{id}")
    EntityModel<Meal> getMealById(@PathVariable String id) {
        Meal meal = mealsRepository.findMeal(id).orElseThrow(() -> new MealNotFoundException(id));

        return mealToEntityModel(id, meal);
    }

    @GetMapping("/rest/meals")
    CollectionModel<EntityModel<Meal>> getMeals() {
        Collection<Meal> meals = mealsRepository.getAllMeal();

        List<EntityModel<Meal>> mealEntityModels = new ArrayList<>();
        for (Meal m : meals) {
            EntityModel<Meal> em = mealToEntityModel(m.getId(), m);
            mealEntityModels.add(em);
        }
        return CollectionModel.of(mealEntityModels,
                linkTo(methodOn(MealsRestController.class).getMeals()).withSelfRel());
    }

    @GetMapping("/rest/cheapestMeal")
    CollectionModel<EntityModel<Meal>> getCheapestMeal() {
        List<Meal> cheapestMeals = mealsRepository.getCheapestMeal();

        if (cheapestMeals.isEmpty()) {
            return CollectionModel.empty(); // Return an empty collection if there are no meals
        }

        List<EntityModel<Meal>> cheapestMealEntityModels = new ArrayList<>();
        for (Meal meal : cheapestMeals) {
            EntityModel<Meal> em = mealToEntityModel(meal.getId(), meal);
            cheapestMealEntityModels.add(em);
        }

        Link selfLink = linkTo(methodOn(MealsRestController.class).getCheapestMeal()).withSelfRel();

        return CollectionModel.of(cheapestMealEntityModels, selfLink);
    }

    @GetMapping("/rest/largestMeal")
    CollectionModel<EntityModel<Meal>> getLargestMeal() {
        List<Meal> largestMeals = mealsRepository.getLargestMeal();

        if (largestMeals.isEmpty()) {
            return CollectionModel.empty(); // Return an empty collection if there are no meals
        }

        List<EntityModel<Meal>> largestMealEntityModels = new ArrayList<>();
        for (Meal meal : largestMeals) {
            EntityModel<Meal> em = mealToEntityModel(meal.getId(), meal);
            largestMealEntityModels.add(em);
        }

        Link selfLink = linkTo(methodOn(MealsRestController.class).getLargestMeal()).withSelfRel();

        return CollectionModel.of(largestMealEntityModels, selfLink);
    }

    @PostMapping("/rest/addMeal")
    public ResponseEntity<EntityModel<Meal>> addMeal(@RequestBody Meal newMeal) {
        mealsRepository.addMeal(newMeal);

        EntityModel<Meal> addedMealEntityModel = mealToEntityModel(newMeal.getId(), newMeal);
        Link selfLink = linkTo(methodOn(MealsRestController.class).addMeal(newMeal)).withSelfRel();

        return ResponseEntity
                .created(selfLink.toUri())
                .body(addedMealEntityModel);
    }

    @PutMapping("/rest/updateMeal")
    public ResponseEntity<EntityModel<Meal>> updateMeal(@RequestBody Meal updatedMeal) {
        mealsRepository.updateMeal(updatedMeal);

        EntityModel<Meal> updatedMealEntityModel = mealToEntityModel(updatedMeal.getId(), updatedMeal);
        Link selfLink = linkTo(methodOn(MealsRestController.class).updateMeal(updatedMeal)).withSelfRel();

        return ResponseEntity.ok()
                .body(updatedMealEntityModel);
    }

    @DeleteMapping("/rest/deleteMeal/{mealId}")
    public ResponseEntity<Void> deleteMeal(@PathVariable String mealId) {
        mealsRepository.deleteMeal(mealId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rest/getOrder/{orderId}")
    public ResponseEntity<EntityModel<Order>> getOrder(@PathVariable String orderId) {
        Order order = mealsRepository.getOrder(orderId);

        if (order != null) {
            EntityModel<Order> orderEntityModel = orderToEntityModel(orderId, order);
            return ResponseEntity.ok(orderEntityModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rest/getAllOrders")
    public CollectionModel<EntityModel<Order>> getAllOrders() {
        Collection<Order> allOrders = mealsRepository.getAllOrders();

        if (allOrders.isEmpty()) {
            return CollectionModel.empty(); // Return an empty collection if there are no orders
        }

        List<EntityModel<Order>> orderEntityModels = new ArrayList<>();
        for (Order order : allOrders) {
            EntityModel<Order> em = orderToEntityModel(order.getOrderId(), order);
            orderEntityModels.add(em);
        }

        Link selfLink = linkTo(methodOn(MealsRestController.class).getAllOrders()).withSelfRel();

        return CollectionModel.of(orderEntityModels, selfLink);
    }

    @PostMapping("/rest/placeOrder")
    public ResponseEntity<EntityModel<Order>> placeOrder(@RequestBody Order newOrder) {
        mealsRepository.addOrder(newOrder);

        EntityModel<Order> placedOrderEntityModel = orderToEntityModel(newOrder.getOrderId(), newOrder);
        Link selfLink = linkTo(methodOn(MealsRestController.class).placeOrder(newOrder)).withSelfRel();

        return ResponseEntity
                .created(selfLink.toUri())
                .body(placedOrderEntityModel);
    }

    private EntityModel<Meal> mealToEntityModel(String id, Meal meal) {
        return EntityModel.of(meal,
                linkTo(methodOn(MealsRestController.class).getMealById(id)).withSelfRel(),
                linkTo(methodOn(MealsRestController.class).getMeals()).withRel("rest/meals"));
    }

    private EntityModel<Order> orderToEntityModel(String orderId, Order order) {
        return EntityModel.of(order,
                linkTo(methodOn(MealsRestController.class).getAllOrders()).withRel("allOrders"),
                linkTo(methodOn(MealsRestController.class).getOrder(orderId)).withSelfRel());
    }




}
