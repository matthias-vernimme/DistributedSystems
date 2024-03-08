package be.kuleuven.foodrestservice.controllers;

import be.kuleuven.foodrestservice.domain.Meal;
import be.kuleuven.foodrestservice.domain.MealsRepository;
import be.kuleuven.foodrestservice.domain.Order;
import be.kuleuven.foodrestservice.exceptions.MealNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class MealsRestRpcStyleController {

    private final MealsRepository mealsRepository;

    @Autowired
    MealsRestRpcStyleController(MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
    }

    @GetMapping("/restrpc/meals/{id}")
    Meal getMealById(@PathVariable String id) {
        Optional<Meal> meal = mealsRepository.findMeal(id);

        return meal.orElseThrow(() -> new MealNotFoundException(id));
    }

    @GetMapping("/restrpc/meals")
    Collection<Meal> getMeals() {
        return mealsRepository.getAllMeal();
    }

    @GetMapping("/restrpc/cheapestMeal")
    List<Meal> getCheapestMeal() {
        return mealsRepository.getCheapestMeal();
    }

    @GetMapping("/restrpc/largestMeal")
    List<Meal> getLargestMeal() {
        return mealsRepository.getLargestMeal();
    }

    @PostMapping("/restrpc/addMeal/{meal}")
    void addMeal(@PathVariable Meal meal) {
        mealsRepository.addMeal(meal);
    }

    @PutMapping("/restrpc/updateMeal/{meal}")
    void updateMeal(@PathVariable Meal meal) {
        mealsRepository.updateMeal(meal);
    }

    @DeleteMapping("/restrpc/deleteMeal/{id}")
    void deleteMeal(@PathVariable String id) {
        mealsRepository.deleteMeal(id);
    }

    @PostMapping("/restrpc/placeOrder")
    void placeOrder(@RequestBody Order order) {
        mealsRepository.addOrder(order);
    }

    @GetMapping("/restrpc/getAllOrders")
    Collection<Order> getAllOrders() {
        return mealsRepository.getAllOrders();
    }

    @GetMapping("/restrpc/getOrder/{id}")
    Order getOrder(@PathVariable String id) {
        return mealsRepository.getOrder(id);
    }

}
