package com.example.algamoney.api.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsFilter implements Filter {

    String origemPermitida = ""; // TODO: Configurar para diferentes ambientes;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;

        resp.setHeader("Access-Control-Allow-Origin", this.origemPermitida);
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        if ("OPTIONS".equals(req.getMethod()) && this.origemPermitida.equals(req.getHeader("Origin"))) {
            resp.setHeader("Access-Control-Allow-Methods", "OPTIONS, POST, GET, PUT, DELETE");
            resp.setHeader("Access-Control-Allow-Headers", "Authorization, Content-type, Accept");
            resp.setHeader("Access-Control-Nax-Age", "3600");
        } else {
            chain.doFilter(request, response);
        }

    }

}
