package com.yourlhub.domain.models;

import java.util.Arrays;
import java.util.List;

public record SampleAuthor(String id, String firstName, String lastName) {

  private static List<SampleAuthor> sampleAuthors =
      Arrays.asList(
          new SampleAuthor("author-1", "Joshua", "Bloch"),
          new SampleAuthor("author-3", "Douglas", "Adams"),
          new SampleAuthor("author-2", "Bill", "Bryson"));

  public static List<SampleAuthor> getByIds(List<String> ids) {
    return sampleAuthors.stream().filter(sampleAuthor -> ids.contains(sampleAuthor.id())).toList();
  }
}
