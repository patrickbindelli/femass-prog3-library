package br.edu.femass.library_system.model;

public enum Genero {
    FANTASIA("Fantasia"),
    FICCAO_CIENTIFICA("Ficcao cientifica"),
    DISTOPIA("Distopia"),
    ACAO_E_AVENTURA("Ação e Aventura"),
    FICCAO_POLICIAL("Ficção Policial"),
    HORROR("Horror"),
    THRILLER_E_SUSPENSE("Thriller e Suspense"),
    FICCAO_HISTORICA("Ficção Histórica"),
    ROMANCE("Romance"),
    FICCAO_FEMININA("Ficcção Feminina"),
    LGBTQ("LGBTQ"),
    FICCAO_CONTEMPORANEA("Ficcção Comtemporanea"),
    REALISMO_MAGICO("Realismo Mágico"),
    GRAPHIC_NOVEL("Graphic Novel"),
    CONTO("Conto"),
    JOVEM_ADULTO("Jovem Adulto"),
    NOVO_ADULTO("Novo Adulto"),
    INFANTIL("Infantil"),
    MEMORIAS_E_AUTOBIOGRAFIA("Memórias e Autobiografia"),
    BIOGRAFIA("Biografia"),
    GASTRONOMIA("Gastronomia"),
    ARTE_E_FOTOGRAFIA("Artes e Fotografia"),
    AUTOAJUDA("Autoajuda"),
    HISTORIA("História"),
    VIAGEM("Viagem"),
    CRIMES_REAIS("Crimes Reais"),
    HUMOR("Humor"),
    ENSAIOS("Ensaios"),
    GUIAS_E_COMO_FAZER("Guias e Como Fazer"),
    RELIGIAO_E_ESPIRITUALIDADE("Religião e Espiritualidade"),
    HUMANIDADES_E_CIENCIAS_SOCIAIS("Humanidades e Ciencias Sociais"),
    PATERNIDADE_E_FAMILIA("Paternidade e Familia"),
    TECNOLOGIA_E_CIENCIA("Tecnologia e Ciencia");

    private final String nome;

    Genero(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
