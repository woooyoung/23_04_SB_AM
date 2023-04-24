package com.KoreaIT.cwy.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.cwy.demo.repository.ArticleRepository;
import com.KoreaIT.cwy.demo.repository.BoardRepository;
import com.KoreaIT.cwy.demo.vo.Board;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;

	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	public Board getBoardById(int boardId) {
		return boardRepository.getBoardById(boardId);
	}

}
