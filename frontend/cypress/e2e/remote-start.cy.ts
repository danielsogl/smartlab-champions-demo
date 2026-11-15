describe('remote start', () => {
  it('starts a session on an available charge point', () => {
    cy.visit('/charge-points/CP-1')
    cy.contains('button', 'Remote start').click()
    cy.get('output').should('contain', 'Session started')
  })

  it('AC3: explains why a charge point in maintenance cannot be started', () => {
    cy.visit('/charge-points/CP-7')
    cy.contains('button', 'Remote start').should('be.disabled')
    cy.contains('In maintenance')
  })
})
