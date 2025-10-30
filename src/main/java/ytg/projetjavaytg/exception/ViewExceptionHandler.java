package ytg.projetjavaytg.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Handler global pour les contrôleurs de vues (@Controller).
 * Renvoie des redirections + messages flash (pas de JSON, donc pas de 500 visibles côté page).
 */
@ControllerAdvice(annotations = Controller.class)
public class ViewExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(ResourceNotFoundException ex,
                                 RedirectAttributes ra,
                                 HttpServletRequest request) {
        ra.addFlashAttribute("error", ex.getMessage());
        return redirectBack(request, "/");
    }

    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequest(BadRequestException ex,
                                   RedirectAttributes ra,
                                   HttpServletRequest request) {
        ra.addFlashAttribute("error", ex.getMessage());
        return redirectBack(request, "/");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleConstraint(DataIntegrityViolationException ex,
                                   RedirectAttributes ra,
                                   HttpServletRequest request) {
        ra.addFlashAttribute("error", "Données invalides ou contrainte violée (champ manquant, doublon, FK…).");
        return redirectBack(request, "/");
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception ex,
                                RedirectAttributes ra,
                                HttpServletRequest request) {
        ra.addFlashAttribute("error", "Une erreur est survenue : " + ex.getMessage());
        return redirectBack(request, "/");
    }

    private String redirectBack(HttpServletRequest request, String defaultUrl) {
        String referer = request.getHeader("Referer");
        if (referer == null || referer.isBlank()) {
            return "redirect:" + defaultUrl;
        }
        // évite les redirections sur endpoints POST en revenant sur la page précédente
        return "redirect:" + referer;
    }
}
