package xyz.uben._4cface.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.uben.common.domain.model.Grid;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben._4cface.application.SearchService;
import xyz.uben._4cface.domain.model.Doc;

import java.util.List;

/**
 * @author hupanfeng
 * @since 16/11/7
 */
@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("/grid")
    @ResponseBody
    public Grid grid(String key, String category, PageParameter page) {Grid grid = new Grid();
        List<Doc> docs = searchService.search(key, category, page);
        grid.setRows(docs);
        grid.setTotal(page.getTotalCount());
        return grid;
    }
}
