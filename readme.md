# Awesome Hotel

[TOC]

Copyright©Awesome Hotel

## Background

Nanjing University Software Institute Software Engineering and Computing Ⅱ 2020 Spring Awesome Hotel Group

This project develops a hotel manage system for administer, client, marketing staff, hotel manager.

## Install

This project users JAVA, MySQL 5, maven and npm. Go check them out if you don't have them locally installed.

```bash
# install maven dependency
Awesome Hotel $ mvn install
# run front end
front_end $ npm install
front_end $ npm run serve
```

Run hotel.sql

Run HotelApplication.java to start back end

## Usage

You can use this project to order hotel, manage hotel, manage coupon, register as vip

For detailed introduction, go and read our document

## Related Efforts

* [Ant Design Vue](www.antdv.com)
* [Spring Boot](https://spring.io/projects/spring-boot)

## Maintainers

* 程荣鑫 @Sparrow612
* 郭礼华 @garyGlh
* 韩禧@AdianH
* 庄子元 @NIL-zhuang

## Notice

该项目主要基于Github的private仓库进行开发，seec作为一个辅助代码仓库，提交记录较少。本仓库内的具体提交量如下(截至2020年6月24日21点09分)

提交日志详见[Git commit Log](document/gitlog.md)

```bash
$ git log | grep "^Author: " | awk '{print $2}' | sort | uniq -c | sort -k1,1nr
    125 Cheng
     79 NIL_zhuang
     78 guo
     42 hanxi
      2 dependabot[bot]
```

```bash
$ git log --format='%aN' | sort -u | while read name; do echo -en "$name\t"; git log --author="$name" --pretty=tformat: --numstat | grep "\(.sql\|.vue\|.java\|.xml\|.properties\|.css\|.js\|.txt\|.md\|.html\)$" | awk '{ add += $1; subs += $2; loc += $1 - $2 } END { printf "added lines: %s, removed lines: %s, total lines: %s\n", add, subs, loc }' -; done
Cheng Rongxin   added lines: 15386, removed lines: 11283, total lines: 4103
dependabot[bot] added lines: , removed lines: , total lines:
guo lihua       added lines: 5940, removed lines: 1176, total lines: 4764
hanxi   added lines: 2365, removed lines: 895, total lines: 1470
NIL_zhuang      added lines: 90011, removed lines: 3817, total lines: 86194
```

## TODO

图片上传功能已实现在AliyunOSSController.java中，由于前后端连接问题，该功能暂时搁置开发。
