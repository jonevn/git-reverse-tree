package com.github.jonevn.git.reverse.tree.logic.domain;

import java.util.Date;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Commit implements Comparable<Commit> {

	private String id;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private String shortId;

	@EqualsAndHashCode.Exclude
	private String message;

	@EqualsAndHashCode.Exclude
	private Date commitTime;

	@EqualsAndHashCode.Exclude
	private String authorName;

	@EqualsAndHashCode.Exclude
	private String authorEmail;

	@Override
	public int compareTo(Commit other) {
		return this.commitTime.compareTo(other.commitTime);
	}

	public String tooltipText() {
		return toString().replaceAll(",", "\n");
	}

}