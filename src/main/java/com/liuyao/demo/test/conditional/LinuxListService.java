package com.wisely.highlight_spring4.ch3.conditional;

//2-3
public class LinuxListService implements ListService {

    @Override
    public String showListCmd() {
        return "ls";
    }
}
