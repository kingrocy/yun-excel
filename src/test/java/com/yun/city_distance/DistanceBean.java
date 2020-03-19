package com.yun.city_distance;

import lombok.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Date : 2020/3/19 11:22 上午
 * @Author : dushaoyun
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DistanceBean {

    private String source;
    private String target;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistanceBean that = (DistanceBean) o;
        return (Objects.equals(source, that.source) &&
                Objects.equals(target, that.target)) || (Objects.equals(source, that.target) &&
                Objects.equals(target, that.source));
    }

    @Override
    public int hashCode() {
        char[] sourceCharArray = source.toCharArray();
        char[] targetCharArray = target.toCharArray();
        List<Character> list=new ArrayList<>();
        for (char c : targetCharArray) {
            list.add(c);
        }
        for (char c : sourceCharArray) {
            list.add(c);
        }
        Collections.sort(list);
        StringBuilder sb=new StringBuilder();
        for (Character character : list) {
            sb.append(character);
        }
        return Objects.hashCode(sb.toString());
    }


    public static void main(String[] args) {
        DistanceBean distanceBean1=new DistanceBean("北京","上海");
        DistanceBean distanceBean2=new DistanceBean("上海","北京");
        System.out.println(distanceBean1.hashCode());
        System.out.println(distanceBean2.hashCode());

        Map<DistanceBean, Double> distance_cache = new ConcurrentHashMap<>();

        distance_cache.put(distanceBean1, 12d);
        Double aDouble = distance_cache.get(distanceBean2);
        System.out.println(aDouble);

    }
}
