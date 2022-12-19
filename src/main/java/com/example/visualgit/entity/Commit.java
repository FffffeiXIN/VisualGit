package com.example.visualgit.entity;

import lombok.Data;

@Data
public class Commit {
    String id;
    String commit_time;
    String repository_id;
}
