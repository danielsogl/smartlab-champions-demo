describe('remote start', () => {
  it('starts a session on an available charge point', () => {
    cy.visit('/charge-points/CP-1')
    cy.contains('button', 'Remote start').click()
    cy.get('[role=status]').should('contain', 'Session started')
  })
})
