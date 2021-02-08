package net.dontdrinkandroot.gitki.service.markdown

interface MarkdownService {

    fun parseToHtml(source: String): String
}