package comp3111.webscraper;

import java.util.Date;
import java.util.List;


/**
 * Service class that helps obtain the elements with lowest price and latest post
 *
 * @author cal852
 */
public class FindElement {

    /**
     * Task 1
     * Finds and returns back the index of the element in List with the lowest price that is > 0.0
     * @author cal852
     * @param List of results to pick the index from
     * @return the index of element with the lowest price that is > 0.0 or -1 if List of results is NULL
     *         -1 if List of results is empty, -2 if List of results is NULL
     */
    public int findMin(List<Item> result) {
        int minCount = 0;
        double minPrice = 0.0;
        int returnMin = 0;

        if (result != null) {
            if (!result.isEmpty()) {
                // find first non-zero price element of results
                for (; minCount < result.size(); minCount++) {
                    if (result.get(minCount).getPrice() > 0.0) {
                        minPrice = result.get(minCount).getPrice();
                        returnMin = minCount; // in case first element is the smallest element
                        break;
                    }
                }

                for (; minCount < result.size(); minCount++) {
                    if (result.get(minCount).getPrice() < minPrice && result.get(minCount).getPrice() > 0.0) {
                        minPrice = result.get(minCount).getPrice();
                        returnMin = minCount;
                    }
                }

                return returnMin;
            } else {
                return -1;
            }
        } else {
            return -2;
        }
    }

    /**
     * Task 1
     * Finds and returns back the index of the element in List with the most recent (latest) posting date
     * @author cal852
     * @param List of results to pick the index from
     * @return the index of the element with the latest posting date,
     *         -1 if List of results is empty, -2 if List of results is NULL
     */
    public int findLatest(List<Item> result) {
        int returnLatest = 0;
        if (result != null) {
            if (!result.isEmpty()) {
                Date latestDate = result.get(0).getDate();
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getPrice() > 0.0 && result.get(i).getDate().compareTo(latestDate) > 0) {
                        latestDate = result.get(i).getDate();
                        returnLatest = i;
                    }
                }
                return returnLatest;
            } else {
                return -1;
            }
        } else {
            return -2;
        }
    }
}
