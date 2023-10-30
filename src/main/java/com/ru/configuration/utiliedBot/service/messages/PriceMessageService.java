package com.ru.configuration.utiliedBot.service.messages;

import com.ru.configuration.utiliedBot.enums.Price;
import com.ru.configuration.utiliedBot.enums.UtilType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PriceMessageService implements MultipleTypesMessageInterface<UtilType> {
    /**
     * Method returns message by its type
     * @param type - message type
     * @return String represents message ready to be sent
     */
    @Override
    public String getByType(UtilType type) {
        StringBuilder message = new StringBuilder();
        message.append(type.getTitle())
                .append(":")
                .append("\n");
        final List<String> positions = Arrays.stream(Price.values())
                .filter(p -> p.getType().equals(type.ordinal()))
                .map(p -> p.getTitle() + " " + p.getAmount() + " " + p.getCurrency() + "/" + p.getMeasure())
                .collect(Collectors.toList());
        String positionsWithIndexes = IntStream
                .range(0, positions.size())
                .mapToObj(i -> i + " " + positions.get(i))
                .collect(Collectors.joining("\n"));
        message.append(positionsWithIndexes);
        return message.toString();
    }

    /**
     * Method returns composed message by all types
     * @param types - all message types
     * @return String represents message ready to be sent
     */
    @Override
    public String getByAllTypes(Collection<UtilType> types) {
        return types.stream()
                .map(this::getByType)
                .collect(Collectors.joining("\n\n"));
    }
}
