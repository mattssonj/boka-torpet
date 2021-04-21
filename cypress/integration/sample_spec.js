describe('My First Test', () => {
    it('Does not do much!', () => {
        expect(true).to.equal(true)
    })
})

describe('Login to show message', () => {
    it('Visit login page', () => {
        cy.visit('http://localhost:8080')

        cy.contains('Login page')
    })

    it('Login', () => {
        cy.get('#username').type('user')
        cy.get('#password').type('password')
        cy.get('form').submit()

        cy.getCookie('JSESSIONID').should('exist')

        //cy.visit('http://localhost:8080/')
        //cy.contains('Senaste nyheterna')
    })

    it('Sees message', () => {
        cy.visit('http://localhost:8080/')
        cy.contains('Senaste nyheterna')
    })
})