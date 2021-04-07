package org.bansang.dto;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class SearchCriteria extends Criteria {

	private String searchType;
	private String keyword;

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "SearchCriteria [searchType=" + searchType + ", keyword=" + keyword + "]";
	}

	@Override
	public String getURI() {
		UriComponents uriComponents = UriComponentsBuilder.newInstance().queryParam("page", super.page)
				.queryParam("searchType", searchType).queryParam("keyword", keyword).build();
		return uriComponents.toUriString();
	}

}
