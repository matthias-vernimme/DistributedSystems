package be.kuleuven.foodrestservice.domain;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class MealsRepository {
    // map: id -> meal
    private static final Map<String, Meal> meals = new HashMap<>();
    private static final Map<String, Order> orders = new HashMap<>();

    @PostConstruct
    public void initData() {

        Meal a = new Meal();
        a.setId("5268203c-de76-4921-a3e3-439db69c462a");
        a.setName("Steak");
        a.setDescription("Steak with fries");
        a.setMealType(MealType.MEAT);
        a.setKcal(1100);
        a.setPrice((10.00));

        meals.put(a.getId(), a);

        Meal b = new Meal();
        b.setId("4237681a-441f-47fc-a747-8e0169bacea1");
        b.setName("Portobello");
        b.setDescription("Portobello Mushroom Burger");
        b.setMealType(MealType.VEGAN);
        b.setKcal(1100);
        b.setPrice((7.00));

        meals.put(b.getId(), b);

        Meal c = new Meal();
        c.setId("cfd1601f-29a0-485d-8d21-7607ec0340c8");
        c.setName("Fish and Chips");
        c.setDescription("Fried fish with chips");
        c.setMealType(MealType.FISH);
        c.setKcal(950);
        c.setPrice(5.00);

        meals.put(c.getId(), c);
    }

    public Optional<Meal> findMeal(String id) {
        Assert.notNull(id, "The meal id must not be null");
        Meal meal = meals.get(id);
        return Optional.ofNullable(meal);
    }

    public Collection<Meal> getAllMeal() {
        return meals.values();
    }

    public List<Meal> getCheapestMeal() {
        if (meals.isEmpty()) {
            return Collections.emptyList(); // Return an empty list if there are no meals
        }

        List<Meal> cheapestMeals = new ArrayList<>();
        double lowestPrice = Double.MAX_VALUE;

        for (Meal meal : meals.values()) {
            double mealPrice = meal.getPrice();

            if (mealPrice < lowestPrice) {
                lowestPrice = mealPrice;
                cheapestMeals.clear(); // Clear the list if a meal with lower price is found
                cheapestMeals.add(meal);
            } else if (mealPrice == lowestPrice) {
                // Add meals with equal prices to the list
                cheapestMeals.add(meal);
            }
        }

        return cheapestMeals;
    }


    public List<Meal> getLargestMeal() {
        if (meals.isEmpty()) {
            return Collections.emptyList(); // Return an empty list if there are no meals
        }

        List<Meal> largestMeals = new ArrayList<>();
        double highestCalories = Double.MIN_VALUE;

        for (Meal meal : meals.values()) {
            double mealCalories = meal.getKcal();

            if (mealCalories > highestCalories) {
                highestCalories = mealCalories;
                largestMeals.clear(); // Clear the list if a meal with higher calories is found
                largestMeals.add(meal);
            } else if (mealCalories == highestCalories) {
                // Add meals with equal calories to the list
                largestMeals.add(meal);
            }
        }

        return largestMeals;
    }

    public void addMeal(Meal newMeal) {
        Assert.notNull(newMeal, "The meal must not be null");

        // Generate a new ID if not provided
        String mealId = newMeal.getId();
        if (mealId == null || mealId.isEmpty()) {
            mealId = UUID.randomUUID().toString();
            newMeal.setId(mealId);
        }

        meals.put(mealId, newMeal);
    }

    public void updateMeal(Meal updatedMeal) {
        Assert.notNull(updatedMeal, "The updated meal must not be null");
        String mealId = updatedMeal.getId();

        if (meals.containsKey(mealId)) {
            // Update the existing meal with the new values (except for the ID)
            Meal existingMeal = meals.get(mealId);
            existingMeal.setName(updatedMeal.getName());
            existingMeal.setDescription(updatedMeal.getDescription());
            existingMeal.setMealType(updatedMeal.getMealType());
            existingMeal.setKcal(updatedMeal.getKcal());
            existingMeal.setPrice(updatedMeal.getPrice());
        }
        // If the ID does not exist, do nothing (you may choose to handle this differently)
    }

    public void deleteMeal(String mealId) {
        Assert.notNull(mealId, "The meal ID must not be null");
        meals.remove(mealId);
    }

    public void addOrder(Order newOrder) {
        Assert.notNull(newOrder, "The order must not be null");

        // Generate a new order ID if not provided
        String orderId = newOrder.getOrderId();
        if (orderId == null || orderId.isEmpty()) {
            orderId = UUID.randomUUID().toString();
            newOrder.setOrderId(orderId);
        }

        orders.put(orderId, newOrder);
    }

    public Order getOrder(String orderId) {
        Assert.notNull(orderId, "The order ID must not be null");
        return orders.get(orderId);
    }


    public Collection<Order> getAllOrders() {
        return orders.values();
    }


}
