package com.KoreaIT.java.AM.controller;

import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleController {
  private List<Article> articles;
  private Scanner sc;

  public ArticleController(List<Article> articles, Scanner sc) {
    this.sc = sc;
    this.articles = articles;
  }

  public void doWrite() {
    int id = articles.size() + 1;

    String regDate = Util.getNowDateStr();
    System.out.printf("제목 : ");
    String title = sc.nextLine();
    System.out.printf("내용 : ");
    String body = sc.nextLine();

    Article article = new Article(id, regDate, title, body);
    articles.add(article);

    System.out.printf("%d번 글이 생성 되었습니다.\n", id);
  }

  public void showList(String cmd) {
    if (articles.size() == 0) {
      System.out.println("게시글이 없습니다.");
      return;
    } else {
      String searchKeyword = cmd.substring("article list".length()).trim();

      List<Article> forPrintArticles = articles;
      if (searchKeyword.length() > 0) {
        forPrintArticles = new ArrayList<>();

        for (Article article : articles) {
          if (article.title.contains(searchKeyword)) {
            forPrintArticles.add(article);
          }
        }
        if (forPrintArticles.size() == 0) {
          System.out.println("검색결과가 없습니다");
          return;
        }
      }

      System.out.println("번호    |    제목    |    조회수");
      for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
        Article article = forPrintArticles.get(i);
        System.out.printf("%4d    |    %2s    |    %3d\n", article.id, article.title, article.viewCnt);
      }
    }
  }

  public void showDetail(String cmd) {
    String[] cmdBits = cmd.split(" ");
    int id = Integer.parseInt(cmdBits[2]);

    Article foundArticle = getArticleById(id);

    if (foundArticle == null) {
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }

    foundArticle.increaseViewCnt();
    System.out.printf("번호 : %d\n", foundArticle.id);
    System.out.printf("날짜 : %s\n", foundArticle.regDate);
    System.out.printf("제목 : %s\n", foundArticle.title);
    System.out.printf("내용 : %s\n", foundArticle.body);
    System.out.printf("조회수 : %d\n", foundArticle.viewCnt);
  }

  public void doModify(String cmd) {
    String[] cmdBits = cmd.split(" ");
    int id = Integer.parseInt(cmdBits[2]);

    Article foundArticle = getArticleById(id);

    if (foundArticle == null) {
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("제목 : ");
    String title = sc.nextLine();
    System.out.printf("내용 : ");
    String body = sc.nextLine();

    foundArticle.title = title;
    foundArticle.body = body;

    System.out.printf("%d번 글이 수정 되었습니다.\n", id);
  }

  public void doDelete(String cmd) {
    String[] cmdBits = cmd.split(" ");
    int id = Integer.parseInt(cmdBits[2]);

    int foundIdx = getArticleIndexById(id);

    if (foundIdx == -1) {
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }

    articles.remove(foundIdx);
    System.out.printf("%d번 글이 삭제 되었습니다.\n", id);
  }

  private int getArticleIndexById(int id) {
    int i = 0;

    for (Article article : articles) {
      if (article.id == id) {
        return i;
      }
      i++;
    }

    return -1;
  }

  private Article getArticleById(int id) {
    int index = getArticleIndexById(id);
    if (index != -1) {
      return articles.get(index);
    }
    return null;
  }
}
