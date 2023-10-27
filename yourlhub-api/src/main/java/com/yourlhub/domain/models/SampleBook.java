package com.yourlhub.domain.models;

import java.util.Arrays;
import java.util.List;

public record SampleBook(String id, String name, int pageCount, String authorId) {

  private static List<SampleBook> sampleBooks =
      Arrays.asList(
          new SampleBook("book-1", "Effective Java", 416, "author-1"),
          new SampleBook("book-2", "Hitchhiker's Guide to the Galaxy", 208, "author-2"),
          new SampleBook("book-3", "Down Under", 436, "author-4"));

  public static SampleBook getById(String id) {
    return sampleBooks.stream()
        .filter(sampleBook -> sampleBook.id().equals(id))
        .findFirst()
        .orElse(null);
  }

  public static List<SampleBook> get() {
    return sampleBooks;
  }
}
